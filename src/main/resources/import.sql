--INSERT INTO cargo(cargo_nombre) VALUES ('ADMINISTRADOR');
--INSERT INTO cargo(cargo_nombre) VALUES ('LIMPIEZA');
--INSERT INTO cargo(cargo_nombre) VALUES ('MANTENIMIENTO');
--INSERT INTO cargo(cargo_nombre) VALUES ('SOPORTE');


INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('SECRETARIA ACADEMICA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('PSICOLOGIA Y ASISTENCIA SOCIAL','Primer piso, al lado de Laboratorio G');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('IMAGEN INSTITUCIONAL','Segundo piso frente a la direccion');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('TOPICO','Segundo piso al lado derecho del aula de reuniones');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('CALIDAD EDUCATIVA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('OFICINA DE DISENIO GRAFICO','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('ATENCION AL ESTUDIANTE','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('EMPLEABILIDAD Y SEGUIMIENTO DE EGRESADOS','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('FORMACION CONTINUA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('DIRECCION ACADEMICA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('COORDINACION ACADEMICA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('CONTABILIDAD','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('CAJA','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('LABORATORIOS','Segundo piso');
INSERT INTO ubicacion(ubicacion_nombre,descripcion) VALUES('SALONES','Segundo piso');


INSERT INTO tipo_tarea(tipo_tarea_nombre,imagen) VALUES('LIMPIEZA','limpieza.png');
INSERT INTO tipo_tarea(tipo_tarea_nombre,imagen) VALUES('SOPORTE','sopote.png');
INSERT INTO tipo_tarea(tipo_tarea_nombre,imagen) VALUES('MANTENIMIENTO','mantenimiento.png');


INSERT INTO estado(estado_nombre,tipo_estado) VALUES('ASIGNADO','Tarea');
INSERT INTO estado(estado_nombre,tipo_estado) VALUES('PROCESO','Incidencia');
INSERT INTO estado(estado_nombre,tipo_estado) VALUES('PAUSADO','Asistencia');
INSERT INTO estado(estado_nombre,tipo_estado) VALUES('FINALIZADO','Presupuesto');


INSERT INTO prioridad(prioridad_nombre) VALUES ('Baja');
INSERT INTO prioridad(prioridad_nombre) VALUES ('Media');
INSERT INTO prioridad(prioridad_nombre) VALUES ('Alta');

--INSERT INTO usuario(nombre,apellido,telefono,email,estado_activo,fecha_creacion,horario_trabajo,imagen,username,password,id_cargo) VALUES('Don Atelo', 'Sabalate Aguirre', '987654321', 'elmanso@gmail.com','true','2024-09-01','TARDE','user.png','Donatelo','123456',2);
--INSERT INTO usuario(nombre,apellido,telefono,email,estado_activo,fecha_creacion,horario_trabajo,imagen,username,password,id_cargo) VALUES('Jose', 'Ezequiel Mapache', '987654121', 'el@gmail.com','true','2024-09-01','MANIANA','user.png','user2','123456',1);
--INSERT INTO usuario(nombre,apellido,telefono,email,estado_activo,fecha_creacion,horario_trabajo,imagen,username,password,id_cargo) VALUES('Pedro', 'Salas Alvarado', '912345671', 'manso@gmail.com','true','2024-09-01','NOCHE','user.png','user3','123456',3);
--INSERT INTO usuario(nombre,apellido,telefono,email,estado_activo,fecha_creacion,horario_trabajo,imagen,username,password,id_cargo) VALUES('Juan', 'Dante Limon', '976534511', 'so@gmail.com','true','2024-09-01','TARDE','user.png','user4','123456',4);
--INSERT INTO usuario(nombre,apellido,telefono,email,estado_activo,fecha_creacion,horario_trabajo,imagen,username,password,id_cargo) VALUES('Diego', 'Simon Mundo', '999999991', 'man@gmail.com','true','2024-09-01','NOCHE','user.png','user5','123456',2);


--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','Se requiere realizar el Mantenimiento del salon 301 ya que presenta danies el la parte de la puerta',3,false,'2024-09-01T09:12:00',2,3,1,1,10,'TAR2024-01');
--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','',4,false,'2024-09-01T09:12:00',2,3,1,2,12,'TAR2024-02');
--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','',3,false,'2024-09-01T09:12:00',2,1,2,3,10,'TAR2024-03');
--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','Problemas en el audiovisual del Salon de Usos Multiples',5,false,'2024-09-01T09:12:00',2,1,2,2,10,'TAR2024-04');
--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,fecha_finalizacion,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','El aula se encuentra Sucia',1,false,'2024-09-01T09:12:00',2,'2024-09-01T09:12:30',1,4,3,9,'TAR2024-05');
--INSERT INTO tarea(titulo,descripcion,id_usuario,requiere_compra,fecha_creacion,aprobado,fecha_finalizacion,id_tipo_tarea,id_estado,id_prioridad,id_ubicacion,numero_tarea) VALUES ('Mantenimiento de Infraestructura','Problemas con el audio salon 201',4,false,'2024-09-01T09:12:00',2,'2024-09-01T09:12:30',2,4,1,7,'TAR2024-06');




