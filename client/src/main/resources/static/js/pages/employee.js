var table;
var employeeVar = {};
var state;


$(document).ready(function () {
    initDataTable();
    initSelect2();

    $('.sidebar #employee').addClass('active')

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addEmployeeForm();
        state = "CREATE";
    });

    $('#employeeTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoEmployeeForm(data);
        state = "INFO"
    });

    $('#employeeTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editEmployeeForm(data);
        state = "UPDATE";
    });

    $('#employeeTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#employeeForm').submit(function (event) {
        event.preventDefault();
        if (state === 'CREATE') {
            insert();
        } else if (state === 'UPDATE') {
            update();
        }
    });
});

function initSelect2() {
    $.ajax({
        url: '/job/get-all',
        type: 'GET',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.title + "</option>\n";
            });

            $("#employeeForm #job").append(options);
        }
    });

    $('#employeeForm #job').select2({
        theme: "bootstrap"
    });

    $.ajax({
        url: '/employee/get-all',
        type: 'GET',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.firstName + " " + value.lastName + "</option>\n";
            });

            $("#employeeForm #manager").append(options);
        }
    });

    $('#employeeForm #manager').select2({
        theme: "bootstrap"
    });

    $.ajax({
        url: '/department/get-all',
        type: 'GET',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.name + "</option>\n";
            });

            $("#employeeForm #department").append(options);
        }
    });

    $('#employeeForm #department').select2({
        theme: "bootstrap"
    });

}

function initDataTable() {

    table = $('#employeeTable').removeAttr('width').DataTable(
            {
                "scrollX": true,
                scrollCollapse: true,
                'sAjaxSource': '/employee/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'firstName'},
                    {'data': 'lastName'},
                    {'data': 'hireDate'},
                    {'data': 'phoneNumber'},
                    {'data': 'salary'},
                    {'data': 'email'},
                    {'data': 'commissionPct'},

                    {'data': 'job'},
                    {'data': 'manager'},
                    {'data': 'department'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#employeeModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#employeeModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ],
                "columnDefs": [
                    {
                        "targets": 11,
                        "width": 150
                    }
                ],
                fixedColumns: true
            }
    );




}

function infoEmployeeForm(data) {
    console.log("info");
    employeeVar.id = $('#employeeForm #id').val(data.id);
    employeeVar.firstName = $('#employeeForm #firstName').val(data.firstName);
    employeeVar.lastName = $('#employeeForm #lastName').val(data.lastName);
    employeeVar.hireDate = $('#employeeForm #hireDate').val(data.hireDate);
    employeeVar.phoneNumber = $('#employeeForm #phoneNumber').val(data.phoneNumber);
    employeeVar.salary = $('#employeeForm #salary').val(data.salary);
    employeeVar.email = $('#employeeForm #email').val(data.email);
    employeeVar.commissionPct = $('#employeeForm #commissionPct').val(data.commissionPct);

    employeeVar.job = $('#employeeForm #job').val(data.job);
    employeeVar.manager = $('#employeeForm #manager').val(data.manager);
    employeeVar.department = $('#employeeForm #department').val(data.department);

    $('#employeeForm #job').trigger('change');
    $('#employeeForm #manager').trigger('change');
    $('#employeeForm #department').trigger('change');


    $('#employeeForm #id').prop('readonly', true);
    $('#employeeForm #firstName').prop('readonly', true);
    $('#employeeForm #lastName').prop('readonly', true);
    $('#employeeForm #hireDate').prop('readonly', true);
    $('#employeeForm #phoneNumber').prop('readonly', true);
    $('#employeeForm #salary').prop('readonly', true);
    $('#employeeForm #email').prop('readonly', true);
    $('#employeeForm #commissionPct').prop('readonly', true);

    $('#employeeForm #job').prop('disabled', true);
    $('#employeeForm #manager').prop('disabled', true);
    $('#employeeForm #department').prop('disabled', true);

    $('#employeeForm #action-button').hide();
    $('#employeeForm').removeClass('was-validated');
}

function addEmployeeForm() {

    employeeVar.id = $('#employeeForm #id').val(null);
    employeeVar.firstName = $('#employeeForm #firstName').val(null);
    employeeVar.lastName = $('#employeeForm #lastName').val(null);
    employeeVar.hireDate = $('#employeeForm #hireDate').val(null);
    employeeVar.phoneNumber = $('#employeeForm #phoneNumber').val(null);
    employeeVar.salary = $('#employeeForm #salary').val(null);
    employeeVar.email = $('#employeeForm #email').val(null);
    employeeVar.commissionPct = $('#employeeForm #commissionPct').val(null);

    employeeVar.job = $('#employeeForm #job').val(null);
    employeeVar.manager = $('#employeeForm #manager').val(null);
    employeeVar.department = $('#employeeForm #department').val(null);

    $('#employeeForm #job').trigger('change');
    $('#employeeForm #manager').trigger('change');
    $('#employeeForm #department').trigger('change');

    $('#employeeForm #id').prop('readonly', false);
    $('#employeeForm #firstName').prop('readonly', false);
    $('#employeeForm #lastName').prop('readonly', false);
    $('#employeeForm #hireDate').prop('readonly', false);
    $('#employeeForm #phoneNumber').prop('readonly', false);
    $('#employeeForm #salary').prop('readonly', false);
    $('#employeeForm #email').prop('readonly', false);
    $('#employeeForm #commissionPct').prop('readonly', false);

    $('#employeeForm #job').prop('disabled', false);
    $('#employeeForm #manager').prop('disabled', false);
    $('#employeeForm #department').prop('disabled', false);

    $('#employeeForm #action-button').show();
    $('#employeeForm').removeClass('was-validated');
}

function editEmployeeForm(data) {
    employeeVar.id = $('#employeeForm #id').val(data.id);
    employeeVar.firstName = $('#employeeForm #firstName').val(data.firstName);
    employeeVar.lastName = $('#employeeForm #lastName').val(data.lastName);
    employeeVar.hireDate = $('#employeeForm #hireDate').val(data.hireDate);
    employeeVar.phoneNumber = $('#employeeForm #phoneNumber').val(data.phoneNumber);
    employeeVar.salary = $('#employeeForm #salary').val(data.salary);
    employeeVar.email = $('#employeeForm #email').val(data.email);
    employeeVar.commissionPct = $('#employeeForm #commissionPct').val(data.commissionPct);

    employeeVar.job = $('#employeeForm #job').val(data.job);
    employeeVar.manager = $('#employeeForm #manager').val(data.manager);
    employeeVar.department = $('#employeeForm #department').val(data.department);

    $('#employeeForm #job').trigger('change');
    $('#employeeForm #manager').trigger('change');
    $('#employeeForm #department').trigger('change');

    $('#employeeForm #id').prop('readonly', true);
    $('#employeeForm #firstName').prop('readonly', false);
    $('#employeeForm #lastName').prop('readonly', false);
    $('#employeeForm #hireDate').prop('readonly', false);
    $('#employeeForm #phoneNumber').prop('readonly', false);
    $('#employeeForm #salary').prop('readonly', false);
    $('#employeeForm #email').prop('readonly', false);
    $('#employeeForm #commissionPct').prop('readonly', false);

    $('#employeeForm #job').prop('disabled', false);
    $('#employeeForm #manager').prop('disabled', false);
    $('#employeeForm #department').prop('disabled', false);

    $('#employeeForm #action-button').show();
    $('#employeeForm').removeClass('was-validated');
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
                url: '/employee?id=' + data.id,
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

function setEmployee() {
    employeeVar.id = $('#employeeForm #id').val();
    employeeVar.firstName = $('#employeeForm #firstName').val();
    employeeVar.lastName = $('#employeeForm #lastName').val();
    employeeVar.hireDate = $('#employeeForm #hireDate').val();
    employeeVar.phoneNumber = $('#employeeForm #phoneNumber').val();
    employeeVar.salary = $('#employeeForm #salary').val();
    employeeVar.email = $('#employeeForm #email').val();
    employeeVar.commissionPct = $('#employeeForm #commissionPct').val();

    employeeVar.job = $('#employeeForm #job').val();
    employeeVar.manager = $('#employeeForm #manager').val();
    employeeVar.department = $('#employeeForm #department').val();

}

function insert() {
    setEmployee();
    $.ajax({
        url: '/employee',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(employeeVar),
        success: function (res) {
            $('#employeeModal').modal('hide');
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
    console.log("Update");
    setEmployee();
    $.ajax({
        url: '/employee',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(employeeVar),
        success: function (res) {
            $('#employeeModal').modal('hide');
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