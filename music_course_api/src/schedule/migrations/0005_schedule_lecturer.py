# Generated by Django 3.2.12 on 2023-11-15 10:46

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('lecturer', '0001_initial'),
        ('schedule', '0004_remove_schedule_lecturer'),
    ]

    operations = [
        migrations.AddField(
            model_name='schedule',
            name='lecturer',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, related_name='lecturer', to='lecturer.lecturer'),
            preserve_default=False,
        ),
    ]
