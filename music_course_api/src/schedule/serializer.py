from rest_framework import serializers

from src.schedule.models import Schedule;
from src.lecturer.serializer import LectureSerializer;
from src.lecturer.models import Lecturer;


class ScheduleSerializer(serializers.HyperlinkedModelSerializer):  
    lecturer = serializers.ReadOnlyField(source='lecturer.id')

    class Meta:
        model = Schedule
        fields = ["id","lecturer","start_schedule","end_schedule"]
