CREATE DATABASE GestionTareas;
USE GestionTareas;

CREATE TABLE cargo (
        id INT NOT NULL AUTO_INCREMENT,
        cargo_nombre VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE comentario (
        comentario_tarea INT,
        comentario_usuario INT,
        fecha_creacion DATETIME(6) NOT NULL,
        id INT NOT NULL AUTO_INCREMENT,
        comentario VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE estado (
        id INT NOT NULL AUTO_INCREMENT,
        estado_nombre VARCHAR(30) NOT NULL,
        tipo_estado VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE prioridad (
        id INT NOT NULL AUTO_INCREMENT,
        prioridad_nombre VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;


    CREATE TABLE tarea (
        requiere_compra bit,
        aprobado_tarea INT,
        fecha_creacion DATETIME(6) NOT NULL,
        fecha_finalizacion DATETIME(6) NOT NULL,
        id INT NOT NULL AUTO_INCREMENT,
        tarea_estado INT,
        tarea_prioridad INT,
        tarea_tipo INT,
        tarea_ubicacion INT,
        usuario_tarea INT,
        descripcion VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE tipo_tarea (
        id INT NOT NULL AUTO_INCREMENT,
        tipo_tarea_nombre VARCHAR(30) NOT NULL,
        imagen VARBINARY(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE ubicacion (
        id INT NOT NULL AUTO_INCREMENT,
        descripcion VARCHAR(30) NOT NULL,
        ubicacion_nombre VARCHAR(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE usuario (
        remuneracion float(53) NOT NULL,
        fecha_creacion DATETIME(6) NOT NULL,
        id INT NOT NULL AUTO_INCREMENT,
        id_cargo INT,
        apellido VARCHAR(30) NOT NULL,
        email VARCHAR(30) NOT NULL,
        estado_activo VARCHAR(30) NOT NULL,
        horario_trabajo VARCHAR(30) NOT NULL,
        nombre VARCHAR(30) NOT NULL,
        telefono VARCHAR(30) NOT NULL,
        imagen VARBINARY(30) NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    ALTER TABLE comentario
       ADD CONSTRAINT FKfslhd45ot8452jme1utlbcmhe
       FOREIGN KEY (comentario_tarea)
       REFERENCES tarea (id);

    ALTER TABLE comentario
       ADD CONSTRAINT FK9pxfp295caomrhpnoytuockdk
       FOREIGN KEY (comentario_usuario)
       REFERENCES usuario (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FKehtns37mkaymxcc2lvh8vq5kb
       FOREIGN KEY (aprobado_tarea)
       REFERENCES usuario (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FK3mhplxol3g2j964bmr8vb3rlq
       FOREIGN KEY (tarea_estado)
       REFERENCES estado (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FKo0t1ln310es20obtp85qgnt6i
       FOREIGN KEY (tarea_prioridad)
       REFERENCES prioridad (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FK3fhmx9q990v1jfnc9be23ejin
       FOREIGN KEY (tarea_tipo)
       REFERENCES tipo_tarea (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FKkjda8s3p17d6hocrtmawc80jv
       FOREIGN KEY (tarea_ubicacion)
       REFERENCES ubicacion (id);

    ALTER TABLE tarea
       ADD CONSTRAINT FKjfbmndchr8ig4shx5dagpb1ds
       FOREIGN KEY (usuario_tarea)
       REFERENCES usuario (id);

    ALTER TABLE usuario
       ADD CONSTRAINT FK5utj31agad5p0bsrltruei7tu
       FOREIGN KEY (id_cargo)
       REFERENCES cargo (id);
