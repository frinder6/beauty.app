/**
 * frinder_liu
 *
 * @date 2016-04-06 09:40
 */
(function ($) {

    'use strict';

    var defaults = {
        url: '',
        data: [],
        remote: false,
        group: false
    };

    var MySelect2 = function (element, options) {
        this.$this = element;
        this.$opts = options;
    };

    MySelect2.prototype = {
        constructor: MySelect2,
        init: function () {
            var options = $.extend(true, {}, defaults, this.$opts);
            if (options.remote) {
                $.ajax({
                    type: 'POST',
                    url: _PATH(options.url),
                    data: options.data,
                    dataType: 'JSON',
                    async: false,
                    success: function (data) {
                        // alert(JSON.stringify(data));
                        options.data = data;
                    },
                    error: function (msg) {
                        console.error(JSON.stringify(msg));
                    }
                });
            }
            if (!options.group) {
                var data = [];
                $(options.data).each(function (i, d) {
                    var dt = {};
                    dt.id = d.id;
                    dt.text = d.text;
                    data.push(dt);
                });
                options.data = data;
            }
            // alert(JSON.stringify(options));
            return options;
        },
        select: function () {
            var options = this.init();
            var $this = this.$this;
            $this.select2({
                width: '100%',
                placeholder: '请选择...',
                allowClear: true,
                // minimumInputLength: 1,
                data: options.data
            });
        },
        setDefaultValue: function (v) {
            var $this = this.$this;
            $this.val(v);
            $this.trigger('change');
        }
    };


    $.fn.MySelect2 = function (options) {
        var select = new MySelect2(this, options);
        select.select();
        return select;
    };

    $.fn.MySelect2.Constructor = MySelect2;

})(jQuery);