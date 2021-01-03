var table;
var country = {};
var state;


$(document).ready(function () {
    initDataTable();
    initSelect2();

    $('.sidebar #country').addClass('active')

    $('#addRow').on('click', function () {                              // Assign add onclick action
        addCountryForm();
        state = "CREATE";
    });

    $('#countryTable tbody').on('click', '#info', function () {      // Assign info onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        infoCountryForm(data);
        state = "INFO"
    });

    $('#countryTable tbody').on('click', '#edit', function () {      // Assign edit onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        editCountryForm(data);
        state = "UPDATE";
    });

    $('#countryTable tbody').on('click', '#delete', function () {    // Assign delete onclick action for every row
        var data = table.row($(this).parents('tr')).data();
        deleteRow(data);
    });

    $('#countryForm').submit(function (event) {
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
        url: '/region/get-all',
        type: 'GET',

        success: function (data) {
            var options = "";

            $.each(data, function (index, value) {
                options += '<option value="' + value.id + '">' + value.name + "</option>\n";
            });


            $("#countryForm #region").append(options);
        }
    });

    $('#countryForm #region').select2({
        theme: "bootstrap"
    });
}

function initDataTable() {
    console.log("init table");
    table = $('#countryTable').DataTable(
            {
                'sAjaxSource': '/country/get-all',
                'sAjaxDataProp': '',
                'columns': [
                    {'data': 'id'},
                    {'data': 'name'},
                    {'data': 'region'},
                    {
                        'render': function (data, type, row, meta) {
                            return '<button id="info" class="btn btn-sm btn-primary mr-2" data-toggle="modal" data-target="#countryModal">'
                                    + '<i class="fas fa-info-circle"></i></button>'
                                    + '<button id="edit" class="btn btn-sm btn-success mr-3" data-toggle="modal" data-target="#countryModal">'
                                    + '<i class="fas fa-edit"></i></button>'
                                    + '<button id="delete" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>';
                        }
                    }
                ]
            }
    );


}

function infoCountryForm(data) {
    country.id = $('#countryForm #id').val(data.id);
    country.name = $('#countryForm #name').val(data.name);
    country.regionId = $('#countryForm #region').val(data.regionId);
    $('#countryForm #region').trigger('change');




    $('#countryForm #id').prop('readonly', true);
    $('#countryForm #name').prop('readonly', true);
    $('#countryForm #region').prop('disabled', true);


    $('#countryForm #action-button').hide();
    $('#countryForm').removeClass('was-validated');
}

function addCountryForm() {
    country.id = $('#countryForm #id').val(null);
    country.name = $('#countryForm #name').val(null);
    country.regionId = $('#countryForm #region').val(null);
    $('#countryForm #region').trigger('change');




    $('#countryForm #id').prop('readonly', false);
    $('#countryForm #name').prop('readonly', false);
    $('#countryForm #region').prop('disabled', false);


    $('#countryForm #action-button').show();
    $('#countryForm').removeClass('was-validated');
}

function editCountryForm(data) {
    country.id = $('#countryForm #id').val(data.id);
    country.name = $('#countryForm #name').val(data.name);
    country.regionId = $('#countryForm #region').val(data.regionId);
    $('#countryForm #region').trigger('change');




    $('#countryForm #id').prop('readonly', true);
    $('#countryForm #name').prop('readonly', false);
    $('#countryForm #region').prop('disabled', false);


    $('#countryForm #action-button').show();
    $('#countryForm').removeClass('was-validated');
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
                url: '/country?id=' + data.id,
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

function setCountry() {
    country.id = $('#countryForm #id').val();
    country.name = $('#countryForm #name').val();
    country.regionId = $('#countryForm #region').val();

}

function insert() {
    setCountry();
    $.ajax({
        url: '/country',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(country),
        success: function (res) {
            $('#countryModal').modal('hide');
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
    setCountry();
    $.ajax({
        url: '/country',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(country),
        success: function (res) {
            $('#countryModal').modal('hide');
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