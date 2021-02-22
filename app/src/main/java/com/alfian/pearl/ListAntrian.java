package com.alfian.pearl;

class ListAntrian {
    String id_antrian, status, username, total_waktu;

    public ListAntrian()
    {
    }

    public ListAntrian(String id_antrian, String status, String username, String total_waktu)
    {
        this.id_antrian = id_antrian;
        this.status = status;
        this.username = username;
        this.total_waktu = total_waktu;
    }

    //Methode Getter & Setter
    public String getId_antrian()
    {
        return id_antrian;
    }

    public void setId_antrian(String id_antrian)
    {
        this.id_antrian = id_antrian;
    }
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getTotal_waktu()
    {
        return total_waktu;
    }

    public void setTotal_waktu(String total_waktu)
    {
        this.total_waktu = total_waktu;
    }
}
