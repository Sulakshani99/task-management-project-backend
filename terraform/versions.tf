terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  # Remote state backend for GitHub Actions
  # Replace bucket name and account-specific values before use
  backend "s3" {
    bucket         = "task-management-terraform-state-YOUR_ACCOUNT_ID"
    key            = "task-management-app/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-state-lock"
  }
}
