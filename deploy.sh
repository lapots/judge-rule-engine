#!/bin/bash

set -o errexit -o nounset

terraform init
terraform plan

terraform destroy -target heroku_app.judge_re -force

terraform apply -auto-approve
