<?php

include('Connection.php');

$username    = $_POST['username'];
$password    = $_POST['password'];

if(!empty($username) || !empty($password)){

    $sqlCheck = "SELECT COUNT(*) FROM staff WHERE username='$username'";
    $queryCheck = mysqli_query($conn,$sqlCheck);
    $hasilCheck = mysqli_fetch_array($queryCheck);
    if($hasilCheck[0] == 0){
        $sql = "INSERT INTO staff (username,password) VALUES('$username','$password')";

        $query = mysqli_query($conn,$sql);

        if(mysqli_affected_rows($conn) > 0){
            $data['status'] = true;
            $data['result'] = "Berhasil";
        }else{
            $data['status'] = false;
            $data['result'] = "Gagal";
        }
    }else{
        $data['status'] = false;
        $data['result'] = "Gagal, Data Sudah Ada";
    }



}
else{
    $data['status'] = false;
    $data['result'] = "Gagal, Nomor Induk dan Nama tidak boleh kosong!";
}


print_r(json_encode($data));




?>