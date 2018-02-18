#!/bin/bash

set -o errexit -o nounset

terraform init

terraform plan -destroy
terraform destroy -target heroku_app.judge_re -force

terraform plan
terraform apply -auto-approve
