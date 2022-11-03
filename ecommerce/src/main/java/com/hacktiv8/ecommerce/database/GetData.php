<?php
include('Connection.php');

$type    = $_POST['type'];
$gender    = $_POST['gender'];
$category    = $_POST['category'];

if(!empty($type)){

    if ($type == "clothing_acc"){
        $sql = "SELECT * FROM clothing_acc WHERE gender = '$gender' AND category = '$category'";
    } else if ($type == "electronics"){
        $sql = "SELECT * FROM electronics WHERE category = '$category'";
    } else if ($type == "book"){
        $sql = "SELECT * FROM book";
    } else if ($type == "other"){
        $sql = "SELECT * FROM other";
    }

    $query = mysqli_query($conn,$sql);

    if(mysqli_affected_rows($conn) > 0){
        while($row = mysqli_fetch_object($query)){
                $data['status'] = true;
                $data['result'][] = $row;

                // $data2 = respond(true, $row);
            }
    }else{
        $data['status'] = false;
        $data['result'] = "Data tidak ditemukan!";
    }
} else{
      $data['status'] = false;
      $data['result'] = "Data tidak boleh kosong";
  }

print_r(json_encode($data));

?>