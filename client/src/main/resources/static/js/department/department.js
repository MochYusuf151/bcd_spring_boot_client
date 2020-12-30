var table;
var department = {};
var state;

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

$(document).ready(function () {
    getAll();
    selectData();

    $('#departmentForm #manager').select2({
     theme: "bootstrap"
    });

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addDepartmentForm();
        state = "CREATE";
    });

    $('#departmentTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoDepartmentForm(data);
        state = "INFO"
    });

    $('#departmentTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editDepartmentForm(data);
        state = "UPDATE";
    });

    $('#departmentTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#departmentForm').submit(function (event) {
        event.preventDefault();
        if (state === 'CREATE') {
            insert();
        } else if (state === 'UPDATE') {
            update();
        }
    });
});

function selectData() {
    //Make an Ajax request to a PHP script called car-models.php
    //This will return the data that we can add to our Select element.
    $.ajax({
        url: '/department/get-managers',
        type: 'get',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.firstName + " " + value.lastName + "</option>\n";
            });

            console.log(options);

            $("#departmentForm #manager").append(options);

        }
    });

}

function getAll() {

    table = $('#departmentTable').DataTable(
            {
                'sAjaxSource': '/department/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'name'},
                    {'data': 'managerId'},
                    {'data': 'manager'},
                    {'data': 'locationId'},
                    {'data': 'location'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#departmentModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#departmentModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ],
                "columnDefs": [
                    {
                        "targets": [2],
                        "visible": false,
                        "searchable": false
                    },
                    {
                        "targets": [4],
                        "visible": false,
                        "searchable": false
                    }
                ]
            }
    );


}

function infoDepartmentForm(data) {
    department.id = $('#departmentForm #id').val(data.id);
    department.name = $('#departmentForm #name').val(data.name);
    department.managerId = $('#departmentForm #manager').val(data.managerId);
    department.locationId = $('#departmentForm #location').val(data.locationId);

    $('#departmentForm #id').prop('readonly', true);
    $('#departmentForm #name').prop('readonly', true);
    $('#departmentForm #manager').prop('disabled', true);
    $('#departmentForm #location').prop('readonly', true);

    $('#departmentForm #action-button').hide();
    $('#departmentForm').removeClass('was-validated');
}

function addDepartmentForm() {
    department.id = $('#departmentForm #id').val(null);
    department.name = $('#departmentForm #name').val(null);
    department.managerId = $('#departmentForm #manager').val(null);
    $('#departmentForm #manager').trigger('change'); 
    
    department.locationId = $('#departmentForm #location').val(null);

    $('#departmentForm #id').prop('readonly', false);
    $('#departmentForm #name').prop('readonly', false);
    $('#departmentForm #manager').prop('disabled', false);
    $('#departmentForm #location').prop('readonly', false);

    $('#departmentForm #action-button').show();
    $('#departmentForm').removeClass('was-validated');
}

function editDepartmentForm(data) {
    department.id = $('#departmentForm #id').val(data.id);
    department.name = $('#departmentForm #name').val(data.name);
    department.managerId = $('#departmentForm #manager').val(data.managerId);
    $('#departmentForm #manager').trigger('change'); 
    
    department.locationId = $('#departmentForm #location').val(data.locationId);

    $('#departmentForm #id').prop('readonly', true);
    $('#departmentForm #name').prop('readonly', false);
    $('#departmentForm #manager').prop('disabled', false);
    $('#departmentForm #location').prop('readonly', false);

    $('#departmentForm #action-button').show();
    $('#departmentForm').removeClass('was-validated');
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
                url: '/department?id=' + data.id,
                type: 'DELETE',
                contentType: 'application/json',
                data: null,
                success: function (res) {
                    table.destroy();
                    getAll();
                    Toast.fire({
                        icon: 'success',
                        title: 'Data deleted',
                        timer: 2000,
                        timerProgressBar: true
                    })
                },
                error: function (e) {
                    sweetAlert("Delete failed");
                }
            });
        }
    })
}

function setDepartment() {
    department.id = $('#departmentForm #id').val();
    department.name = $('#departmentForm #name').val();
    department.managerId = $('#departmentForm #manager').val();
    department.locationId = $('#departmentForm #location').val();
}

function insert() {
    setDepartment();
    $.ajax({
        url: '/department',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(department),
        success: function (res) {
            $('#departmentModal').modal('hide');
            table.destroy();
            getAll();
        },
        error: function (e) {
            sweetAlert("Failed save");
        }
    });
}

function update() {
    console.log($('.js-data-example-ajax').find(':selected').data('name'));
    setDepartment();
    $.ajax({
        url: '/department',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(department),
        success: function (res) {
            $('#departmentModal').modal('hide');
            table.destroy();
            getAll();
            sweetAlert("Saved");
        },
        error: function (e) {
            sweetAlert("Failed save");
        }
    });
}

function sweetAlert(message) {

}