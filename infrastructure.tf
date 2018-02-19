variable "heroku_api_key" {}

provider "heroku" {
    email = "sebastrident@gmail.com"
    api_key = "${var.heroku_api_key}"
}

resource "heroku_app" "judge_re" {
    name = "judge-re"
    region = "us"

    buildpacks = [
        "heroku/java"
    ]
}
