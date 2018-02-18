#!/bin/bash

set -o errexit -o nounset

terraform init
terraform plan

terraform destroy -force

terraform apply -auto-approve
