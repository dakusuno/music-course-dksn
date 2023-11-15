from django.shortcuts import render

from django.shortcuts import render

from src.lecturer.models import Lecturer

from .models import Schedule
from rest_framework.response import Response
from rest_framework import status
from .serializer import ScheduleSerializer
from rest_framework.views import APIView

# Create your views here.
class ScheduleView(APIView):
    def get(self,request, *args, **kwargs):
        serializer_context = {
                'request':request
        }
        
        lecturers = Schedule.objects.all()

        serializer = ScheduleSerializer(lecturers,many=True,context=serializer_context)

        return Response({'data':serializer.data},status=status.HTTP_200_OK)
        
    def post(self, request, *args, **kwargs):
        try:
            serializer_context = {
                'request':request
            }
            
            lecturer_id = request.data.get('lecturer') 
            
            lecturer = Lecturer.objects.get(id=lecturer_id)

            schedule = Schedule(id=None, lecturer=lecturer,start_schedule=request.data.get("start_schedule"),end_schedule=request.data.get("end_schedule"),)

            schedule.save()

            serializer = ScheduleSerializer(schedule,context=serializer_context)
            
            return Response(serializer.data ,status=status.HTTP_200_OK)
        except Lecturer.DoesNotExist:
             return Response({"errors": {
                "message":"A Not Found"
            }}, status=status.HTTP_404_NOT_FOUND)
        
        except Exception:
             return Response({"errors": {
                "message":"something went wrong"
            }}, status=status.HTTP_404_NOT_FOUND)
                



class ScheduleDetailView(APIView):
    def get(self,request,schedule_id, *args, **kwargs):
        schedule = self.get_object(schedule_id)

        
        if not schedule:
            return Response(
                {"res": "Invalid Lecturer ID!"}, 
                status=status.HTTP_400_BAD_REQUEST
            )
        serializer = ScheduleSerializer(schedule)
       
        return Response(serializer.data, status=status.HTTP_200_OK)
       

    def get_object(self,schedule_id):
        '''
        Helper method to get the object with given lecture_id
        '''
        try:
            return Schedule.objects.get(id=schedule_id)
        except Schedule.DoesNotExist:
            return

    def put(self, request, schedule_id, *args, **kwargs):
        '''
        Updates the todo item with given lecture_id if exists
        '''

        lecturer = self.get_object(schedule_id)
        if not schedule_id:
            return Response(
                {"res": "Invalid Lecturer ID!"}, 
                status=status.HTTP_400_BAD_REQUEST
            )
        
        data = {
                'lecturer': request.data.get('lecturer'), 
                'start_schedule': request.data.get('start_schedule'), 
                'end_schedule': request.data.get('end_schedule')
        }
       
        serializer = ScheduleSerializer(instance = lecturer, data=data, partial = True)
       
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)