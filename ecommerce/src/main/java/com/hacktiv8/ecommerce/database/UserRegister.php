<?php

include('Connection.php');

$nama     = $_POST['nama'];
$email    = $_POST['email'];
$password = $_POST['password'];

if(!empty($nama) || !empty($password) || !empty($email)){

    $sqlCheck = "SELECT COUNT(*) FROM user WHERE email='$email'";
    $queryCheck = mysqli_query($conn,$sqlCheck);
    $hasilCheck = mysqli_fetch_array($queryCheck);
    if($hasilCheck[0] == 0){
        $sql = "INSERT INTO user (nama,email,password) VALUES('$nama','$email','$password')";

        $query = mysqli_query($conn,$sql);

        if(mysqli_affected_rows($conn) > 0){
            $data['status'] = true;
            $data['result'] = "Berhasil registrasi akun";
        }else{
            $data['status'] = false;
            $data['result'] = "Email/Password tidak sesuai! Periksa kembali!";
        }
    }else{
        $data['status'] = false;
        $data['result'] = "Email sudah terdaftar";
    }

}
else{
    $data['status'] = false;
    $data['result'] = "Gagal, data tidak boleh kosong";
}


print_r(json_encode($data));




?>