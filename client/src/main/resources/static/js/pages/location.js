var table;
var locationVar = {};
var state;


$(document).ready(function () {
    initDataTable();
    initSelect2();

    $('.sidebar #location').addClass('active')

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addLocationForm();
        state = "CREATE";
    });

    $('#locationTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoLocationForm(data);
        state = "INFO"
    });

    $('#locationTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editLocationForm(data);
        state = "UPDATE";
    });

    $('#locationTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#locationForm').submit(function (event) {
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
        url: '/country/get-all',
        type: 'GET',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.name + "</option>\n";
            });


            $("#locationForm #country").append(options);
        }
    });

    $('#locationForm #country').select2({
        theme: "bootstrap"
    });
}

function initDataTable() {
    
    table = $('#locationTable').DataTable(
            {
                'sAjaxSource': '/location/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'streetAddress'},
                    {'data': 'postalCode'},
                    {'data': 'city'},
                    {'data': 'stateProvince'},
                    {'data': 'country'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#locationModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#locationModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ],
                "columnDefs": [
                    {
                        "targets": [6],
                        "width": "15%"
                    }
                ]
            }
    );


}

function infoLocationForm(data) {
    locationVar.id = $('#locationForm #id').val(data.id);
    locationVar.streetAddress = $('#locationForm #streetAddress').val(data.streetAddress);
    locationVar.postalCode = $('#locationForm #postalCode').val(data.postalCode);
    locationVar.city = $('#locationForm #city').val(data.city);
    locationVar.stateProvince = $('#locationForm #stateProvince').val(data.stateProvince);

    locationVar.country = $('#locationForm #country').val(data.country);
    $('#locationForm #country').trigger('change');




    $('#locationForm #id').prop('readonly', true);
    $('#locationForm #streetAddress').prop('readonly', true);
    $('#locationForm #postalCode').prop('readonly', true);
    $('#locationForm #city').prop('readonly', true);
    $('#locationForm #stateProvince').prop('readonly', true);
    $('#locationForm #country').prop('disabled', true);


    $('#locationForm #action-button').hide();
    $('#locationForm').removeClass('was-validated');
}

function addLocationForm() {
    locationVar.id = $('#locationForm #id').val(null);
    locationVar.streetAddress = $('#locationForm #streetAddress').val(null);
    locationVar.postalCode = $('#locationForm #postalCode').val(null);
    locationVar.city = $('#locationForm #city').val(null);
    locationVar.stateProvince = $('#locationForm #stateProvince').val(null);

    locationVar.country = $('#locationForm #country').val(null);
    $('#locationForm #country').trigger('change');




    $('#locationForm #id').prop('readonly', false);
    $('#locationForm #streetAddress').prop('readonly', false);
    $('#locationForm #postalCode').prop('readonly', false);
    $('#locationForm #city').prop('readonly', false);
    $('#locationForm #stateProvince').prop('readonly', false);
    $('#locationForm #country').prop('disabled', false);


    $('#locationForm #action-button').show();
    $('#locationForm').removeClass('was-validated');
}

function editLocationForm(data) {
    locationVar.id = $('#locationForm #id').val(data.id);
    locationVar.streetAddress = $('#locationForm #streetAddress').val(data.streetAddress);
    locationVar.postalCode = $('#locationForm #postalCode').val(data.postalCode);
    locationVar.city = $('#locationForm #city').val(data.city);
    locationVar.stateProvince = $('#locationForm #stateProvince').val(data.stateProvince);

    locationVar.country = $('#locationForm #country').val(data.country);
    $('#locationForm #country').trigger('change');
    
    

    $('#locationForm #id').prop('readonly', true);
    $('#locationForm #streetAddress').prop('readonly', false);
    $('#locationForm #postalCode').prop('readonly', false);
    $('#locationForm #city').prop('readonly', false);
    $('#locationForm #stateProvince').prop('readonly', false);
    $('#locationForm #country').prop('disabled', false);


    $('#locationForm #action-button').show();
    $('#locationForm').removeClass('was-validated');
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
        text: "You will delete '" + data.streetAddress + "' from database",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {

            $.ajax({
                url: '/location?id=' + data.id,
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

function setLocation() {
    locationVar.id = $('#locationForm #id').val();
    locationVar.streetAddress = $('#locationForm #streetAddress').val();
    locationVar.postalCode = $('#locationForm #postalCode').val();
    locationVar.city = $('#locationForm #city').val();
    locationVar.stateProvince = $('#locationForm #stateProvince').val();

    locationVar.country = $('#locationForm #country').val();

}

function insert() {
    
    setLocation();
    
    $.ajax({
        url: '/location',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(locationVar),
        success: function (res) {
            $('#locationModal').modal('hide');
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
    setLocation();
    $.ajax({
        url: '/location',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(locationVar),
        success: function (res) {
            $('#locationModal').modal('hide');
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