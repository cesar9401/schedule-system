<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { Subject } from '@/model/schedule';
import SubjectService from '@/services/SubjectService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';

const subjects: UnwrapNestedRefs<{ data: Subject[] }> = reactive({ data: [] });

async function findAll() {
  const subject1 = await SubjectService.findAll();
  subjects.data = subject1.data ?? [];
}

onMounted(() => {
  findAll();
});
</script>

<template>
  <div class="container">
    <Header to='/subject/add' :type-header="HeaderEnum.HEADER_LIST_VIEW"></Header>

    <div class="my-5">
      <h1 class="text-success text-center">Cursos</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">C&oacute;digo</th>
            <th scope="col">Nombre</th>
            <th scope="col">Cr&eacute;ditos</th>
            <th scope="col">Obligatorio</th>
            <th scope="col">&Iacute;ndice</th>
            <th class="th-action" scope="col">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-for="subject of subjects.data">
            <td>{{ subject.code }}</td>
            <td>{{ subject.name }}</td>
            <td>{{ subject.numberOfCredits }}</td>
            <td>{{ subject.required ? 'Si' : 'No' }}</td>
            <td>{{ subject.subjectIndex }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center">
                <router-link :to="'/subject/' + subject.subjectId" class="btn btn-sm btn-outline-success">
                  <span class="material-symbols-outlined">edit</span>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
