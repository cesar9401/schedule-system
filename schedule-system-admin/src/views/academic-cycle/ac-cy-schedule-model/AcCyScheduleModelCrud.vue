<script setup lang="ts">

import Header from '@/components/Header.vue';
import { CategoryEnum } from '@/model/CategoryEnum';
import { HeaderEnum } from '@/model/HeaderEnum';
import { AcCyScheduleModel, Category } from '@/model/schedule.model';
import router from '@/router';
import AcCyScheduleModelService from '@/services/AcCyScheduleModelService';
import CategoryService from '@/services/CategoryService';
import { onMounted, reactive, ref, UnwrapNestedRefs } from 'vue';
import VueMultiselect from 'vue-multiselect';

const acCyScheduleModel = reactive({ data: new AcCyScheduleModel() });
const catSubjectOrder: UnwrapNestedRefs<{ data: Category[] }> = reactive({ data: [] });
const catSO = reactive({ data: new Category() });

const academicCycleId = ref(1);

async function create() {
  try {
    const response = await AcCyScheduleModelService.save(academicCycleId.value, catSO.data, acCyScheduleModel.data);
    window.alert('Cambios guardados!');
    await router.push({ name: 'all-ac-cy-models' });
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong');
  }
}

async function getData() {
  try {
    const catSO = await CategoryService.findByParentInternalId(CategoryEnum.SUBJECT_ORDER);
    catSubjectOrder.data = [...catSO.data];
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong!');
  }
}

onMounted(() => {
  const acCyId = router.currentRoute.value.params['academicCycleId'];
  academicCycleId.value = Number(acCyId);
  getData();
});

</script>

<template>
  <div class="container">
    <Header :type-header="HeaderEnum.HEADER_CRUD" :to="'/academic-cycle/' + academicCycleId + '/model'" title="Regresar a simulaciones" />
    <div class="my-5">
      <h1 class="text-success text-center">Agregar simulaci&oacute;n</h1>
    </div>
    <form @submit.prevent="create">
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Usuario responsable</label>
          <input v-model="acCyScheduleModel.data.responsibleUser" id="name" class="form-control" placeholder="Eje: Jose Perez Leon">
        </div>
        <div class="mb-3 col-lg-6 col-12">
          <label for="description">Descripci&oacute;n</label>
          <input v-model="acCyScheduleModel.data.description" id="description" class="form-control" placeholder="Eje: Esta es la simulacion del modelo...">
        </div>
        <div class="mb-3 col-lg-6 col-12">
          <label>Orden de cursos</label>
          <VueMultiselect v-model="catSO.data" :options="catSubjectOrder.data" track-by="categoryId" label="description" class="w-100"></VueMultiselect>
        </div>
      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-2">Guardar</button>
      </div>
    </form>
  </div>
</template>
