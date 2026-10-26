// Discover every stable release line from the existing CI minimum.
const metadataUrl = 'https://api.adoptium.net/v3/info/available_releases';
const compare = (a, b) => a.localeCompare(b, 'en', {numeric: true});

module.exports = async function discover({core, fetch = globalThis.fetch}) {
  const response = await fetch(metadataUrl, {
    headers: {'User-Agent': 'sailthru-ci-runtime-matrix'},
    signal: AbortSignal.timeout(30000)
  });
  if (!response.ok) throw new Error(`Runtime metadata request failed: ${response.status}`);
  const releases = await response.json();
  // The feature release is GA; tip_version/most_recent_feature_version may be EA.
  const latest = releases.most_recent_feature_release;
  if (!Number.isSafeInteger(latest) || latest < 17 || latest > 272) {
    throw new Error('Invalid latest stable Java feature release');
  }
  const versions = Array.from({length: latest - 16}, (_, index) => String(index + 17));

  const matrix = [...new Set(versions)].sort(compare);
  if (!matrix.includes('17') || matrix.length > 256) {
    throw new Error('Runtime matrix is incomplete or exceeds the GitHub Actions limit');
  }
  core.info(`Testing java release lines: ${matrix.join(', ')}`);
  core.setOutput('versions', JSON.stringify(matrix));
};
