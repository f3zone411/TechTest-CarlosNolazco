# TechTest-CarlosNolazco 
La dirección IP de la API desplegada en Google Cloud es: 104.154.183.67

---Para la autenticación y obtener el token mediante postman debemos utilizar los datos de acceso siguientes---  

POST: 104.154.183.67/authenticate  

{  
    "username":"techtester",  
    "password":"23people"  
}  

GET - 104.154.183.67/courses/ (Lista de Cursos Paginada)  
GET - 104.154.183.67/students/ (Lista de Estudiantes Paginada)  
GET - 104.154.183.67/courses/all (Todos los cursos)  
GET - 104.154.183.67/students/all (Todos los estudiantes)  
GET - 104.154.183.67/students/+"idStudent" (Obtener un estudiante por su id)  
GET - 104.154.183.67/courses/+"idCourse" (Obtener un curso por su id)  
POST -  104.154.183.67/students/ (Agregar un estudiante)  
POST -  104.154.183.67/courses/ (Agregar un curso)  
POST - 104.154.183.67/courses/addstudent/+"idStudent"+/+"idCourse" (Agrega un estudiante a un curso)  
DELETE - 104.154.183.67/courses/+"idCourse" (Elimina un curso)  
DELETE - 104.154.183.67/students/+"idStudent" (Elimina un estudiante)  
PUT - 104.154.183.67/students/+"idStudent" (Modifica la información de un estudiante)  
PUT - 104.154.183.67/courses/+"idCourse" (Modifica la información de un curso)  
