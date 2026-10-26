const {test} = require('node:test');
const assert = require('node:assert/strict');
const discover = require('./runtime-matrix.cjs');

async function resolve(releases) {
  let output;
  await discover({
    core: {info() {}, setOutput(name, value) {
      assert.equal(name, 'versions');
      output = JSON.parse(value);
    }},
    fetch: async () => ({ok: true, json: async () => releases})
  });
  return output;
}

test('includes intermediate and future stable releases, excludes prereleases and duplicates', async () => {
  const releases = {most_recent_feature_release: 30, most_recent_feature_version: 31, tip_version: 32};
  assert.deepEqual(await resolve(releases), Array.from({length: 14}, (_, index) => String(index + 17)));
});

test('fails rather than quietly running an empty or incomplete matrix', async () => {
  await assert.rejects(resolve({}));
  await assert.rejects(resolve({most_recent_feature_release: 16}));
  await assert.rejects(resolve(null));
});

test('metadata HTTP, JSON and network failures fail discovery without emitting a matrix', async () => {
  const core = {info() {}, setOutput() {assert.fail('Must not emit a fallback matrix');}};
  await assert.rejects(discover({core, fetch: async () => ({ok: false, status: 503})}), /503/);
  await assert.rejects(discover({core, fetch: async () => ({
    ok: true, json: async () => {throw new SyntaxError('Invalid JSON');}
  })}), /Invalid JSON/);
  await assert.rejects(discover({core, fetch: async () => {throw new Error('Network failure');}}), /Network failure/);
});
