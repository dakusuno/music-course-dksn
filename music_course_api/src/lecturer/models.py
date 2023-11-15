from django.db import models

from django.utils.translation import gettext_lazy as _

class Lecturer(models.Model):
    
    class Course(models.TextChoices):
        GUITAR = 'GU', _('Guitar')
        PIANO = 'PI', _('Piano')
        VOCAL = 'VO', _('Vocal')
        DRUM = 'DR', _('Drum')

    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    course = models.CharField(
        max_length=2,
        choices=Course.choices,
    )

def __str__(self):
    return self.first_name + ' - ' + self.last_name + '-' + self.course.choices
