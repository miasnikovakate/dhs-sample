import datetime
import os

import boto3
import jwt
import requests

s3_client = boto3.client('s3')
ssm_client = boto3.client('ssm')

host = os.environ['DHS_HOST']
s3_bucket = os.environ['S3_BUCKET']


def lambda_handler(event, context):
    private_key = ssm_client.get_parameter(Name='/jwt/test/private_key')['Parameter']['Value']
    current_time = datetime.datetime.utcnow()
    token = {
        'exp': current_time + datetime.timedelta(seconds=60),
        'nbf': current_time,
        'iss': 'dhs-sample-client',
        'aud': 'dhs-sample-server',
        'sub': 'dhs-client',
        'iat': current_time
    }
    encoded_token = jwt.encode(token, private_key, algorithm='RS256').decode("utf-8")
    print('Encoded token: {}'.format(encoded_token))

    headers = {
        'Authorization': 'Bearer {}'.format(encoded_token)
    }
    current_date = datetime.datetime.now().date()
    get_data(current_date, 'facilities', headers)
    get_data(current_date, 'surgeries', headers)
    get_data(current_date, 'practices', headers)
    get_data(current_date, 'patients', headers)


def get_data(current_date, path, headers):
    url = '{}/{}'.format(host, path)
    print('Request to {}'.format(url))
    response = requests.get(url, headers=headers)
    response_body = response.json() if response is not None and response.status_code == 200 else None
    print('Received response with status_code = {}: {}'.format(response.status_code, response_body))
    if response_body is not None:
        s3_client.put_object(
            Body=str(response_body),
            Bucket=s3_bucket,
            Key='{}/{}.json'.format(path, current_date)
        )
