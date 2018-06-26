<template>
  <div class="email-composer container-fluid">
    <b-form @submit.prevent="publishEmail">
    	<div class="row m-3">
    		<div class="col">
      			<b-button type="submit" variant="primary">Send Email</b-button>
      			<b-button type="reset" @click="resetForm" variant="danger">Reset</b-button>

						<clip-loader :loading="progress" class="float-right"></clip-loader>
    		</div>
    	</div>
    	<div class="row m-3">
    		<div class="col">
				<b-form-group id="toGroup"
                    label="To:"
                    label-for="to">
			        <input-tag 
			        	id="to"
								:tags.sync="form.toRecipients" 
								limit=3 
								validate="email"
								class="form-control">
			        </input-tag>
			      </b-form-group>
			      
			      <b-form-group id="ccGroup"
                    label="Cc:"
                    label-for="cc">
			        <input-tag 
			        	id="cc"
								:tags.sync="form.ccRecipients" 
								limit=3 
								validate="email"
								class="form-control">
			        </input-tag>
			      </b-form-group>
			      
			      <b-form-group id="bccGroup"
                    label="Bcc:"
                    label-for="bcc">
			        <input-tag 
			        	id="bcc"
								:tags.sync="form.bccRecipients" 
								limit=3 
								validate="email"
								class="form-control">
			        </input-tag>
			      </b-form-group>

						<b-form-group id="subjectGroup"
                    label="Subject:"
                    label-for="subject">
			        <b-form-input id="subject"
                    type="text"
                    v-model="form.subject">
							</b-form-input>
			      </b-form-group>

						<template v-if="error && responseMessage && responseMessage.length > 0">
							<b-alert variant="danger" show>
								<span v-html="responseMessage"></span>
    					</b-alert>
						</template>

						<template v-if="!error && responseMessage && responseMessage.length > 0">
							<b-alert variant="success" show>
								<span v-html="responseMessage"></span>
    					</b-alert>
						</template>

			</div>
			<div class="col">
				<b-form-group id="emailBodyGroup"
                    label="Body:"
                    label-for="emailBody">
			        <html-editor 
			        	id="emailBody"
								 v-model="form.emailBody"
			        	:editorToolbar=customToolbar>
			        </html-editor>
			      </b-form-group>
			</div>
		</div> 
    </b-form>
  </div>
</template>

<script>
import { publishEmail } from '../services/apiService'

export default {
	name: 'EmailComposer',
	data () {
		return {
			customToolbar: [
				[{ 'header': [false, 1, 2, 3, 4, 5, 6, ] }],
				['bold', 'italic', 'underline', 'strike'],
				[{'align': ''}, {'align': 'center'}, {'align': 'right'}, {'align': 'justify'}],
				[{ 'list': 'ordered'}, { 'list': 'bullet' }, { 'list': 'check' }],
			],
			form: {
				toRecipients: [],
				ccRecipients: [],
				bccRecipients: [],
				subject: '',
				emailBody: ''
			},
			progress: false,
			error: false, 
    	responseMessage: ''
		}
	},
	methods: {

		resetForm: function() {
      this.form.toRecipients = []
      this.form.ccRecipients = []
      this.form.bccRecipients = []
      this.form.subject = ''
      this.form.emailBody = ''

			this.progress = false
			this.error = false
      this.responseMessage = ''
    },

    publishEmail: function() {
      this.progress = true
			this.error = false
      this.responseMessage = ''

			publishEmail(this.form).then((response) => {
        this.resetForm()

				this.responseMessage = response.message
      })
			.catch((response) => {
				this.error = true
				this.progress = false
				if(response.status == 400) {
					this.responseMessage = "<u>Validation Errors</u>: "
					response.data.forEach((error) => {
						this.responseMessage += "<br>" + error.message
					})
				}
				else {
					this.responseMessage = response.code + ":" + response.message
				}

				console.log(this.responseMessage);      
      })
    }
	}
}
</script>

<style>
  #emailBody {
    height: 300px;
  }
</style>
