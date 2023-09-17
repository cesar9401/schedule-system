<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import { Subject } from '@/model/schedule';
import router from '@/router';
import SubjectService from '@/services/SubjectService';
import { onMounted, reactive } from 'vue';

const subject = reactive({ data: new Subject() });

async function create() {
  try {
    const response = await SubjectService.save(subject.data);
    window.alert('Cambios guardados');
    await router.push({ name: 'all-subjects' });
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong!');
  }
}

async function findById(id: number) {
  try {
    const response = await SubjectService.findById(id);
    subject.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong');
    await router.push({ name: 'all-subjects' });
  }
}

onMounted(() => {
  const id = router.currentRoute.value.params['id'];
  if (!id) return;
  findById(Number(id));
});

</script>

<template>
  <div class="container">
    <Header to="/subject" title="Regresar a cursos" :type-header="HeaderEnum.HEADER_CRUD"></Header>

    <div class="my-5">
      <h1 class="text-success text-center">Agregar curso</h1>
    </div>
    <form @submit.prevent="create">
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="code">C&oacute;digo</label>
          <input v-model="subject.data.code" id="code" class="form-control" placeholder="Eje: Art-123">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Nombre</label>
          <input v-model="subject.data.name" id="name" class="form-control" placeholder="Eje: Art">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <label for="credits">Cr&eacute;ditos</label>
          <input v-model="subject.data.numberOfCredits" id="credits" class="form-control" placeholder="Eje: 5">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <label for="index">&Iacute;ndice</label>
          <input v-model="subject.data.subjectIndex" id="index" class="form-control" placeholder="Eje: 0.121">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <div class="input-container toggle-container">
            <label for="required" class="d-flex justify-content-between">
              <span>
                Curso obligatorio: <span class="fw-bolder">{{ subject.data.required ? 'Si' : 'No' }}</span>
              </span>
              <input v-model="subject.data.required" id="required" type="checkbox">
              <span class="toggle">
                <span class="circle"></span>
              </span>
            </label>
          </div>
        </div>

      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-1">Agregar</button>
      </div>
    </form>
  </div>
</template>
