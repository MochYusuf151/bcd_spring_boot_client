var table;
var jobVar = {};
var state;


$(document).ready(function () {
    initDataTable();

    $('.sidebar #job').addClass('active')

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addJobForm();
        state = "CREATE";
    });

    $('#jobTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoJobForm(data);
        state = "INFO"
    });

    $('#jobTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editJobForm(data);
        state = "UPDATE";
    });

    $('#jobTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#jobForm').submit(function (event) {
        event.preventDefault();
        if (state === 'CREATE') {
            insert();
        } else if (state === 'UPDATE') {
            update();
        }
    });
});

function initDataTable() {
    console.log("init table");
    table = $('#jobTable').DataTable(
            {
                'sAjaxSource': '/job/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'title'},
                    {'data': 'maxSalary'},
                    {'data': 'minSalary'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#jobModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#jobModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ]
            }
    );


}

function infoJobForm(data) {
    console.log("info");
    jobVar.id = $('#jobForm #id').val(data.id);
    jobVar.title = $('#jobForm #title').val(data.title);
    jobVar.maxSalary = $('#jobForm #maxSalary').val(data.maxSalary);
    jobVar.minSalary = $('#jobForm #minSalary').val(data.minSalary);

    $('#jobForm #id').prop('readonly', true);
    $('#jobForm #title').prop('readonly', true);
    $('#jobForm #maxSalary').prop('readonly', true);
    $('#jobForm #minSalary').prop('readonly', true);

    $('#jobForm #action-button').hide();
    $('#jobForm').removeClass('was-validated');
}

function addJobForm() {
    jobVar.id = $('#jobForm #id').val(null);
    jobVar.title = $('#jobForm #title').val(null);
    jobVar.maxSalary = $('#jobForm #maxSalary').val(null);
    jobVar.minSalary = $('#jobForm #minSalary').val(null);

    $('#jobForm #id').prop('readonly', false);
    $('#jobForm #title').prop('readonly', false);
    $('#jobForm #maxSalary').prop('readonly', false);
    $('#jobForm #minSalary').prop('readonly', false);

    $('#jobForm #action-button').show();
    $('#jobForm').removeClass('was-validated');
}

function editJobForm(data) {
    jobVar.id = $('#jobForm #id').val(data.id);
    jobVar.title = $('#jobForm #title').val(data.title);
    jobVar.maxSalary = $('#jobForm #maxSalary').val(data.maxSalary);
    jobVar.minSalary = $('#jobForm #minSalary').val(data.minSalary);

    $('#jobForm #id').prop('readonly', true);
    $('#jobForm #title').prop('readonly', false);
    $('#jobForm #maxSalary').prop('readonly', false);
    $('#jobForm #minSalary').prop('readonly', false);

    $('#jobForm #action-button').show();
    $('#jobForm').removeClass('was-validated');
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
        text: "You will delete '" + data.title + "' from database",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {

            $.ajax({
                url: '/job?id=' + data.id,
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

function setJob() {
    jobVar.id = $('#jobForm #id').val();
    jobVar.title = $('#jobForm #title').val();
    jobVar.maxSalary = $('#jobForm #maxSalary').val();
    jobVar.minSalary = $('#jobForm #minSalary').val();
}

function insert() {
    setJob();
    $.ajax({
        url: '/job',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jobVar),
        success: function (res) {
            $('#jobModal').modal('hide');
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
    setJob();
    $.ajax({
        url: '/job',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(jobVar),
        success: function (res) {
            $('#jobModal').modal('hide');
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