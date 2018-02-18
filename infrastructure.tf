variable "HEROKU_API_KEY" {}

provider "heroku" {
    email = "sebastrident@gmail.com"
    api_key = "${var.HEROKU_API_KEY}"
}

resource "heroku_app" "judge_re" {
    name = "judge-re"
    region = "us"

    buildpacks = [
        "heroku/java"
    ]
}
