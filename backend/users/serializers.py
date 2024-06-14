from rest_framework import serializers
from .models import User

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'first_name', 'last_name', 'email', 'phone', 'zipcode', 'country']


    # # hash passwords in the database, override default create function
    # def create(self, validated_data):
    #     #extract password
    #     password = validated_data.pop('password', None)
    #     instance = self.Meta.model(**validated_data) #doesnt include password

    #     if password is not None:
    #         instance.set_password(password) #hashes password
    #     instance.save()
    #     return instance
