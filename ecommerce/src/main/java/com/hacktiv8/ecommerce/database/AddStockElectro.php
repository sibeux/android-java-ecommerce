<?php

include('Connection.php');

$id_electro = $_POST['id_electro'];
$name    = $_POST['name'];
$category    = $_POST['category'];
$quantity    = $_POST['quantity'];
$type = $_POST['type'];

if(!empty($name) || !empty($quantity)){

    $sqlCheck = "SELECT COUNT(*) FROM electronics WHERE id_electro='new'";
    $queryCheck = mysqli_query($conn,$sqlCheck);
    $hasilCheck = mysqli_fetch_array($queryCheck);
    if($hasilCheck[0] == 0){
        $sql = "INSERT INTO electronics (id_electro,name,category,quantity,type)
        VALUES(NULL,'$name','$category','$quantity','$type')";

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