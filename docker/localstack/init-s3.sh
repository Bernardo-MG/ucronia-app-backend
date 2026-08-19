#!/bin/sh
set -eu

BUCKET="${IMAGE_S3_BUCKET:-ucronia-images}"
SAMPLE_FILE="/tmp/metroludik-2026.png"

if ! awslocal s3api head-bucket --bucket "${BUCKET}" 2>/dev/null; then
  awslocal s3api create-bucket \
    --bucket "${BUCKET}" \
    --create-bucket-configuration LocationConstraint=eu-west-1
fi

printf '%s' 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=' \
  | base64 -d > "${SAMPLE_FILE}"

awslocal s3api put-object \
  --bucket "${BUCKET}" \
  --key metroludik-2026.png \
  --body "${SAMPLE_FILE}" \
  --content-type image/png
