from django.db import models
from django.contrib.auth.models import AbstractUser
# Create your models here.
class User(AbstractUser):
    email = models.CharField(max_length=255, unique=True)
    password = models.CharField(max_length=255)
    phone = models.CharField(max_length=30)
    zipcode = models.CharField(max_length=20)
    country = models.CharField(max_length=255)
    username = None
    is_staff = None
    last_login = None
    is_active = None
    is_superuser = None

    USERNAME_FIELD = 'email' # login w/ email, unique identifier.
    REQUIRED_FIELDS = [] 