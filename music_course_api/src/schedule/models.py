from django.db import models
from src.lecturer.models import Lecturer
from django.utils.translation import gettext_lazy as _

class Schedule(models.Model):
    lecturer = models.ForeignKey(Lecturer,on_delete=models.CASCADE,related_name='lecturer_schedule')
    start_schedule = models.DateField()
    end_schedule = models.DateField()

def __str__(self):
    return self.lecturer + ' - ' + self.start_schedule + '-' + self.end_schedule
