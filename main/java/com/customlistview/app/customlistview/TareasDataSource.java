package com.customlistview.app.customlistview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerard on 15/12/2015.
 */
public class TareasDataSource {

    static List TAREAS = new ArrayList<Tarea>();

    static {

        TAREAS.add(new Tarea("Trotar 30 minutos", "08:00", R.mipmap.ic_launcher));
        TAREAS.add(new Tarea("Estudiar análisis técnico", "10:00", R.mipmap.ic_launcher));
        TAREAS.add(new Tarea("Comer 4 rebanadas de manzana", "10:30", R.mipmap.ic_launcher));
        TAREAS.add(new Tarea("Asistir al taller de programación gráfica", "15:45", R.mipmap.ic_launcher));
        TAREAS.add(new Tarea("Consignarle a Marta", "18:00", R.mipmap.ic_launcher));

    }


}

