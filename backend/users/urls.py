from django.urls import path, include
from .views import *

urlpatterns = [
    path('register', registerAPIView.as_view()),
    path('verify_phone', verifyPhoneAPIView.as_view()),
    path('check_code', checkCodeAPIView.as_view())
]