from rest_framework import serializers

from src.schedule.models import Schedule;
from src.lecturer.serializer import LectureSerializer;
from src.lecturer.models import Lecturer;


class ScheduleSerializer(serializers.HyperlinkedModelSerializer):
    # used for many to one relation
    lecturer = serializers.SerializerMethodField()
    
    class Meta:
        model = Schedule
        fields = ["id","lecturer","start_schedule","end_schedule"]
    
    # need to be defined if using [serializers.SerializerMethodField()]
    def get_lecturer(self,obj):
        return LectureSerializer(obj.lecturer).data

