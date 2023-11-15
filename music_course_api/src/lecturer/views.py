from django.shortcuts import render
from src import schedule

from src.lecturer.models import Lecturer
from rest_framework.response import Response
from rest_framework import status
from .serializer import LectureSerializer
from rest_framework.views import APIView

# Create your views h ere.
class LecturerView(APIView):
    def get(self,request, *args, **kwargs):
        '''
        List all lecturer
        '''

        lecturers = Lecturer.objects

        serializer = LectureSerializer(lecturers,many=True)

        return Response(serializer.data,status=status.HTTP_200_OK)
        
    def post(self, request, *args, **kwargs):
        try:
            '''
            Create the lecture
            '''
            data = {
                'first_name': request.data.get('first_name'), 
                'last_name': request.data.get('last_name'), 
                'course': request.data.get('course')
            }

            serializer = LectureSerializer(data=data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data,status=status.HTTP_201_CREATED)
        except schedule.DoesNotExist:
            return Response({"errors": {
                "body": [
                    "ID Not Found"
                ]
            }}, status=status.HTTP_404_NOT_FOUND)
        except Exception:
            return Response({"errors": {
                "body": [
                    "Bad Request"
                ]
            }}, status=status.HTTP_404_NOT_FOUND)
            

class LecturerDetailView(APIView):
    def get(self,request,lecturer_id, *args, **kwargs):
        lecturer = self.get_object(lecturer_id)

        if not lecturer:
            return Response(
                {"res": "Invalid Lecturer ID!"}, 
                status=status.HTTP_400_BAD_REQUEST
            )
        
        serializer = LectureSerializer(lecturer)
       
        return Response(serializer.data, status=status.HTTP_200_OK)
       

    def get_object(self,lecturer_id):
        '''
        Helper method to get the object with given lecture_id
        '''
        try:
            return Lecturer.objects.get(id=lecturer_id)
        except Lecturer.DoesNotExist:
            return

    def put(self, request, lecturer_id, *args, **kwargs):
        '''
        Updates the todo item with given lecture_id if exists
        '''

        lecturer = self.get_object(lecturer_id)
        if not lecturer_id:
            return Response(
                {"res": "Invalid Lecturer ID!"}, 
                status=status.HTTP_400_BAD_REQUEST
            )
        
        data = {
               'first_name': request.data.get('first_name'), 
                'last_name': request.data.get('last_name'), 
                'course': request.data.get('course')
        }
       
        serializer = LectureSerializer(instance = lecturer, data=data, partial = True)
       
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)