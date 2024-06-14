from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import UserSerializer
from .models import User
from rest_framework.exceptions import AuthenticationFailed
from . import verify
# Create your views here.

class registerAPIView(APIView):
    def post(self, request):
        serializer = UserSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)   #if anything not valid, raise exception
        serializer.save()
        return Response({"res": "success"})
    
class verifyPhoneAPIView(APIView):
    def post(self, request):
        phone = request.data.get('phone')
        print(phone)
        verify.send(phone)
        return Response({"res": "success"})

class checkCodeAPIView(APIView):
    def post(self, request):
        code = request.data.get('code')
        phone = request.data.get('phone')
        if verify.check(phone, code):
            return Response({"res": "success"})
        else:
            return Response({"res": "fail"})