<?php

include('Connection.php');

$id_book = $_POST['id_book'];
$name    = $_POST['name'];
$quantity    = $_POST['quantity'];
$type = $_POST['type'];

if(!empty($name) || !empty($quantity)){

    $sqlCheck = "SELECT COUNT(*) FROM book WHERE id_book='new'";
    $queryCheck = mysqli_query($conn,$sqlCheck);
    $hasilCheck = mysqli_fetch_array($queryCheck);
    if($hasilCheck[0] == 0){
        $sql = "INSERT INTO book (id_book,name,quantity,type)
        VALUES(NULL,'$name','$quantity','$type')";

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