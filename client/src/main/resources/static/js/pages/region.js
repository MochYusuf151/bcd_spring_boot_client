var table;
var region = {};
var state;


$(document).ready(function () {
    initDataTable();

    $('.sidebar #region').addClass('active')

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addRegionForm();
        state = "CREATE";
    });

    $('#regionTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoRegionForm(data);
        state = "INFO"
    });

    $('#regionTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editRegionForm(data);
        state = "UPDATE";
    });

    $('#regionTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#regionForm').submit(function (event) {
        event.preventDefault();
        if (state === 'CREATE') {
            insert();
        } else if (state === 'UPDATE') {
            update();
        }
    });
});

function initDataTable() {

    table = $('#regionTable').DataTable(
            {
                'sAjaxSource': '/region/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'name'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#regionModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#regionModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ],
                "columnDefs": [
                    {
                        "targets": 2,
                        "width": 150
                    }
                ],
                fixedColumns: true
            }
    );


}

function infoRegionForm(data) {
    region.id = $('#regionForm #id').val(data.id);
    region.name = $('#regionForm #name').val(data.name);

    $('#regionForm #id').prop('readonly', true);
    $('#regionForm #name').prop('readonly', true);

    $('#regionForm #action-button').hide();
    $('#regionForm').removeClass('was-validated');
}

function addRegionForm() {
    region.id = $('#regionForm #id').val(null);
    region.name = $('#regionForm #name').val(null);

    $('#regionForm #id').prop('readonly', false);
    $('#regionForm #name').prop('readonly', false);

    $('#regionForm #action-button').show();
    $('#regionForm').removeClass('was-validated');
}

function editRegionForm(data) {
    region.id = $('#regionForm #id').val(data.id);
    region.name = $('#regionForm #name').val(data.name);

    $('#regionForm #id').prop('readonly', true);
    $('#regionForm #name').prop('readonly', false);

    $('#regionForm #action-button').show();
    $('#regionForm').removeClass('was-validated');
}

function deleteRow(data) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-danger mr-3',
            cancelButton: 'btn btn-secondary'
        },
        buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You will delete '" + data.name + "' from database",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {

            $.ajax({
                url: '/region?id=' + data.id,
                type: 'DELETE',
                contentType: 'application/json',
                data: null,
                success: function (res) {
                    table.destroy();
                    initDataTable();
                    sweetAlert("success", "Data deleted");
                },
                error: function (e) {
                    sweetAlert("error", "Delete failed");
                }
            });
        }
    })
}

function setRegion() {
    region.id = $('#regionForm #id').val();
    region.name = $('#regionForm #name').val();


}

function insert() {
    setRegion();
    $.ajax({
        url: '/region',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(region),
        success: function (res) {
            $('#regionModal').modal('hide');
            table.destroy();
            initDataTable();
            sweetAlert("success", "Data inserted");
        },
        error: function (e) {
            sweetAlert("error", "Save failed: " + e.responseText);
        }
    });
}

function update() {
    console.log($('.js-data-example-ajax').find(':selected').data('name'));
    setRegion();
    $.ajax({
        url: '/region',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(region),
        success: function (res) {
            $('#regionModal').modal('hide');
            table.destroy();
            initDataTable();
            sweetAlert("success", "Data changed");
        },
        error: function (e) {
            sweetAlert("error", "Save failed: " + e.status);
        }
    });
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

function sweetAlert(icon, message) {
    Toast.fire({
        icon: icon,
        title: message,
        timer: 2000,
        timerProgressBar: true
    })
}