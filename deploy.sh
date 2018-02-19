#!/bin/bash

set -o errexit -o nounset

echo "terraform actions"
terraform plan
terraform destroy -force
terraform plan
terraform apply -auto-approve

echo "updating terraform state"
git config user.name $GH_USER_NAME
git config user.email $GH_USER_EMAIL
git remote add upstream "https://$GH_TOKEN@github.com/$GH_REPO.git"
git checkout master
git add .
NOW = $(TZ=Europe/Minsk date)
git commit -m "tfstate: $NOW [ci skip]"
git push upstream master
