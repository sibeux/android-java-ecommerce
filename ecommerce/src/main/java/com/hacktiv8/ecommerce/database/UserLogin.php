<?php
include('Connection.php');

$email    = $_POST['email'];
$password    = $_POST['password'];

if(!empty($email) || !empty($password)){

    $sql = "SELECT * FROM user WHERE email = '$email' AND password = '$password'";

    $query = mysqli_query($conn,$sql);

    if(mysqli_affected_rows($conn) > 0){
        $data['status'] = true;
        $data['result'] = "Berhasil";
    }else{
        $data['status'] = false;
        $data['result'] = "Email/Password Tidak Sesuai! Periksa Kembali!";
    }
} else{
      $data['status'] = false;
      $data['result'] = "Data tidak boleh kosong";
  }

print_r(json_encode($data));

?>