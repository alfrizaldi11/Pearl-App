package com.alfian.pearl;

class ListMySchedule {
    String id_schedule, jadwal, nama_crew;

    public ListMySchedule()
    {
    }

    public ListMySchedule(String id_schedule, String jadwal, String nama_crew)
    {
        this.id_schedule = id_schedule;
        this.jadwal = jadwal;
        this.nama_crew = nama_crew;
    }

    //Methode Getter & Setter
    public String getId_schedule()
    {
        return id_schedule;
    }

    public void setId_schedule(String id_schedule)
    {
        this.id_schedule = id_schedule;
    }
    public String getJadwal()
    {
        return jadwal;
    }

    public void setJadwal(String jadwal)
    {
        this.jadwal = jadwal;
    }
    public String getNama_crew()
    {
        return nama_crew;
    }

    public void setNama_crew(String nama_crew)
    {
        this.nama_crew = nama_crew;
    }

}
