"""music_course_api URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from src.schedule.views import ScheduleView,ScheduleDetailView
from src.lecturer.views import LecturerView,LecturerDetailView

urlpatterns = [
    path('admin/', admin.site.urls),
    path('lecturer/',LecturerView.as_view()),
    path('lecturer/<int:lecturer_id>/',LecturerDetailView.as_view()),
    path('schedule/',ScheduleView.as_view()),
    path('schedule/<int:schedule_id>/',ScheduleDetailView.as_view()),
]
