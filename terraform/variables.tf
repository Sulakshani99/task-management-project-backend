variable "aws_region" {
  description = "AWS region to deploy to"
  type        = string
  default     = "us-east-1"
}

variable "app_name" {
  description = "Name of my application"
  type        = string
  default     = "task-management-app"
}

variable "environment" {
  description = "Environment name (dev, staging, prod)"
  type        = string
  default     = "dev"
}

variable "db_username" {
  description = "Database username"
  type        = string
  default     = "admin"
}

variable "db_password" {
  description = "Database password"
  type        = string
  sensitive   = true
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t3.micro"
}

variable "db_backup_retention_period" {
  description = "RDS automated backup retention period in days"
  type        = number
  default     = 1
}

variable "app_port" {
  description = "Application port"
  type        = number
  default     = 8080
}

variable "app_count" {
  description = "Number of application instances"
  type        = number
  default     = 2
}

variable "cpu" {
  description = "CPU units for ECS task (256, 512, 1024, 2048, 4096)"
  type        = number
  default     = 512
}

variable "memory" {
  description = "Memory for ECS task in MB"
  type        = number
  default     = 1024
}

variable "tags" {
  description = "Common tags for all resources"
  type        = map(string)
  default = {
    Project   = "Task Management System"
    ManagedBy = "Terraform"
  }
}

variable "ses_verified_email" {
  description = "Verified email for SES (optional)"
  type        = string
  default     = ""
}

variable "ses_username" {
  description = "SES SMTP username"
  type        = string
  sensitive   = true
  default     = ""
}

variable "ses_password" {
  description = "SES SMTP password"
  type        = string
  sensitive   = true
  default     = ""
}

