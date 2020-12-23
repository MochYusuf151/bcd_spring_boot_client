/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('#accordionSidebar #region').addClass('active');
});

function deleteData(id, name) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-danger mr-3',
            cancelButton: 'btn btn-secondary'
        },
        buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You will delete '" + name + "' from database",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            $.post("region/delete", {
                id: id
            }, null);
            Toast.fire({
                icon: 'success',
                title: 'Data deleted',
                timer: 2000,
                timerProgressBar: true
            })
            setTimeout(function () {
                location.reload()
            }, 2000);
        }
    })
}

const Toast = Swal.mixin({
    toast: true,
    position: 'top-right',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

function deleteToast() {


    Toast.fire({
        icon: 'success',
        title: 'Data deleted successfully'
    })
}

function getFormData(id, name, action) {
    $('form').removeClass('was-validated');
    $('form #id').val(id);
    $('form #name').val(name);
    switch (action) {
        case "add":
            $('#regionModalTitle').html("Add New Region");
            $('#region-form').prop("action", "region/post");
            $('#region-form').prop("method", "post");
            break;
        case "detail":
            $('#regionModalTitle').html("Region Info");
            break;
        case "edit":
            $('#regionModalTitle').html("Edit Region");
            $('#region-form').prop("action", "region/update");
            $('#region-form').prop("method", "post");
            break;
        default:
            break;
    }
    setEnableForm(action);
}


function setEnableForm(action) {
    isDisable = action === 'detail';

    if (action === 'add') {
        $('form #id').prop("readonly", false);
    } else {
        $('form #id').prop("readonly", true);
    }
    $('form #name').prop("readonly", isDisable);

    if (isDisable) {
        $('form #action-button').hide();
    } else {
        $('form #action-button').show();
    }
}

function submitForm() {
    if (document.getElementById("region-form").checkValidity()) {
        $('#regionModal').modal('hide');
        Toast.fire({
            icon: 'success',
            title: 'Data Updated',
            timer: 2000
        })
    }
    setTimeout(function () {
        location.reload()
    }, 2000);
}