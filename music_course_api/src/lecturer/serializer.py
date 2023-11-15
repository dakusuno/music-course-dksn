from rest_framework import serializers

from src.lecturer.models import Lecturer;

class LectureSerializer(serializers.HyperlinkedModelSerializer):
    
    class Meta:
        model = Lecturer
        fields = ["id","first_name","last_name","course"]

    def get_object(lecturer_id):
        '''
        Helper method to get the object with given lecture_id
        '''
        try:
            return Lecturer.objects.get(id=lecturer_id)
        except Lecturer.DoesNotExist:
            return
   