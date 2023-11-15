from rest_framework import serializers

from src.lecturer.models import Lecturer;

class LectureSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Lecturer
        fields = ["id","first_name","last_name","course"]
   