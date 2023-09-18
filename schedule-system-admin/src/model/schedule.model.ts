
export class Category {
  categoryId!: number;
  parentCategoryId!: number;
  internalId!: number;
  description!: string;
}

export class Classroom {
  classroomId!: number;
  name!: string;
  recommendedCapacity!: number;
}

export class Subject {
  subjectId!: number;
  code!: string;
  name!: string;
  numberOfCredits: number;
  required: boolean;
  subjectIndex!: number;
}

export class Professor {
  professorId!: number;
  fullName!: string;
  email!: string;
  dateOfHire!: string;
  averageQualification!: number;

  professorSubjects: ProfessorSubject[] = [];
  contractDays: ProfessorContractDay[] = [];
}

export class ProfessorSubject {
  professorSubjectId!: number;
  professor!: Professor;
  subject!: Subject;
  qualification!: number;
  yearsOfExperience: number;
}

export class ProfessorContractDay {
  professorContractDayId!: number;
  professor!: Professor;
  catDay!: Category;
  startTime!: string;
  endTime!: string;

  // for frontend only
  start: any | undefined = { hours: new Date().getHours(), minutes: new Date().getMinutes() };
  end: any | undefined = { hours: new Date().getHours(), minutes: new Date().getMinutes() };
}
