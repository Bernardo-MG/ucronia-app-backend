import base64
import os

import boto3
from botocore.exceptions import ClientError


bucket = os.getenv("IMAGE_S3_BUCKET", "ucronia-images")
region = os.getenv("AWS_DEFAULT_REGION", "eu-west-1")
endpoint = os.getenv("AWS_ENDPOINT_URL", "http://s3:4566")

s3 = boto3.client(
    "s3",
    endpoint_url=endpoint,
    region_name=region,
    aws_access_key_id="test",
    aws_secret_access_key="test",
)

try:
    s3.head_bucket(Bucket=bucket)
except ClientError as error:
    if error.response["ResponseMetadata"]["HTTPStatusCode"] != 404:
        raise

    create_parameters = {"Bucket": bucket}
    if region != "us-east-1":
        create_parameters["CreateBucketConfiguration"] = {
            "LocationConstraint": region,
        }

    s3.create_bucket(**create_parameters)
