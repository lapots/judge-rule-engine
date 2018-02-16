variable "heroku_api_key" {}

provider "heroku" {
    email = "sebastrident@gmail.com"
    api_key = "${var.heroku_api_key}"
}

resource "heroku_app" "default" {
    name = "judge-re"
}
