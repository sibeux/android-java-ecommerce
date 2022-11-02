<?php

include('Connection.php');

$id_cloth = $_POST['id_cloth'];
$name    = $_POST['name'];
$gender    = $_POST['gender'];
$category    = $_POST['category'];
$quantity    = $_POST['quantity'];
$type = $_POST['type'];

if(!empty($username) || !empty($password)){

    $sqlCheck = "SELECT COUNT(*) FROM clothing_acc WHERE id_cloth='$id_cloth'";
    $queryCheck = mysqli_query($conn,$sqlCheck);
    $hasilCheck = mysqli_fetch_array($queryCheck);
    if($hasilCheck[0] == 0){
        $sql = "INSERT INTO clothing_acc (id_cloth,name,gender,category,quantity,type)
        VALUES(NULL,'$name','$gender','$category','$quantity','$type')";

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