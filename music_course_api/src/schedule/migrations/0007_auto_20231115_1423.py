# Generated by Django 3.2.12 on 2023-11-15 14:23

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('schedule', '0006_alter_schedule_lecturer'),
    ]

    operations = [
        migrations.AlterField(
            model_name='schedule',
            name='end_schedule',
            field=models.DateTimeField(),
        ),
        migrations.AlterField(
            model_name='schedule',
            name='start_schedule',
            field=models.DateTimeField(),
        ),
    ]
