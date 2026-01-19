import { useState, useEffect } from 'react';
import { Card, Row, Col, Statistic, Button, Table, Modal, Form, InputNumber, Switch, message, Space } from 'antd';
import { BookOutlined, UserOutlined, TeamOutlined } from '@ant-design/icons';
import { teacherService } from '../../services/teacherService';
import { useAuth } from '../../hooks/useAuth';

const TeacherDashboard = () => {
  const { userId } = useAuth();
  const [subjects, setSubjects] = useState([]);
  const [students, setStudents] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [loading, setLoading] = useState(false);
  const [attendanceModalVisible, setAttendanceModalVisible] = useState(false);
  const [marksModalVisible, setMarksModalVisible] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [attendanceForm] = Form.useForm();
  const [marksForm] = Form.useForm();

  // Statistics
  const [subjectsCount, setSubjectsCount] = useState(0);
  const [studentsCount, setStudentsCount] = useState(0);
  const [coursesCount, setCoursesCount] = useState(0);

  useEffect(() => {
    if (userId) {
      fetchSubjects();
    }
  }, [userId]);

  const fetchSubjects = async () => {
    setLoading(true);
    try {
      const response = await teacherService.getSubjects(userId);
      setSubjects(response || []);
      
      // Calculate statistics
      setSubjectsCount(response?.length || 0);
      
      // Get unique courses
      const uniqueCourses = new Set(response?.map(subject => subject.courseId).filter(Boolean) || []);
      setCoursesCount(uniqueCourses.size);
      
      // Students count will be calculated when viewing students for a subject
      // For now, we'll show 0 or update it when students are fetched
    } catch (error) {
      message.error('Failed to fetch subjects');
      console.error('Error fetching subjects:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchStudents = async (subjectId) => {
    setLoading(true);
    try {
      const response = await teacherService.getSubjectStudents(userId, subjectId);
      setStudents(response || []);
      setSelectedSubject(subjectId);
      // Update students count when students are fetched
      setStudentsCount(response?.length || 0);
    } catch (error) {
      message.error('Failed to fetch students');
      console.error('Error fetching students:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkAttendance = (student) => {
    setSelectedStudent(student);
    attendanceForm.resetFields();
    setAttendanceModalVisible(true);
  };

  const handleMarkMarks = (student) => {
    setSelectedStudent(student);
    marksForm.resetFields();
    setMarksModalVisible(true);
  };

  const handleAttendanceSubmit = async (values) => {
    try {
      await teacherService.markAttendance(
        userId,
        selectedSubject,
        selectedStudent.id,
        values.present
      );
      message.success('Attendance marked successfully');
      setAttendanceModalVisible(false);
      fetchStudents(selectedSubject);
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to mark attendance');
    }
  };

  const handleMarksSubmit = async (values) => {
    try {
      await teacherService.markMarks(
        userId,
        selectedSubject,
        selectedStudent.id,
        values.marks
      );
      message.success('Marks added successfully');
      setMarksModalVisible(false);
      fetchStudents(selectedSubject);
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to add marks');
    }
  };

  const subjectColumns = [
    {
      title: 'Subject Name',
      dataIndex: 'name',
      key: 'name',
      width: '40%',
      align: 'center',
    },
    {
      title: 'Course',
      dataIndex: ['course', 'name'],
      key: 'course',
      width: '30%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '30%',
      align: 'center',
      render: (_, record) => (
        <Button
          type="primary"
          onClick={() => fetchStudents(record.id)}
          size="small"
        >
          View Students
        </Button>
      ),
    },
  ];

  const studentColumns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      width: '50%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '50%',
      align: 'center',
      render: (_, record) => (
        <Space>
          <Button
            type="primary"
            onClick={() => handleMarkAttendance(record)}
            size="small"
          >
            Mark Attendance
          </Button>
          <Button onClick={() => handleMarkMarks(record)} size="small">
            Enter Marks
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div className="h-full flex flex-col justify-evenly" style={{ padding: '1rem' }}>
      <h1 className="text-3xl font-bold mb-6 text-center">Teacher Dashboard</h1>
      
      <Row gutter={[16, 16]} className="mb-6">
        <Col xs={24} sm={12} lg={8}>
          <Card>
            <Statistic
              title="Subjects"
              value={subjectsCount}
              prefix={<BookOutlined />}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} lg={selectedSubject ? 12 : 24}>
          <Card title="My Subjects" className="h-full">
            <Table
              columns={subjectColumns}
              dataSource={subjects}
              loading={loading}
              rowKey="id"
              scroll={{ x: 'max-content' }}
            />
          </Card>
        </Col>

        {selectedSubject && (
          <Col xs={24} lg={12}>
            <Card title="Students" className="h-full">
              <Table
                columns={studentColumns}
                dataSource={students}
                loading={loading}
                rowKey="id"
                scroll={{ x: 'max-content' }}
              />
            </Card>
          </Col>
        )}
      </Row>

      <Modal
        title="Mark Attendance"
        open={attendanceModalVisible}
        onCancel={() => setAttendanceModalVisible(false)}
        footer={null}
        width={500}
      >
        <Form
          form={attendanceForm}
          layout="vertical"
          onFinish={handleAttendanceSubmit}
        >
          <Form.Item
            name="present"
            label="Present"
            valuePropName="checked"
            initialValue={true}
          >
            <Switch checkedChildren="Present" unCheckedChildren="Absent" size="default" />
          </Form.Item>

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setAttendanceModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                Submit
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>

      <Modal
        title="Enter Marks"
        open={marksModalVisible}
        onCancel={() => setMarksModalVisible(false)}
        footer={null}
        width={500}
      >
        <Form
          form={marksForm}
          layout="vertical"
          onFinish={handleMarksSubmit}
        >
          <Form.Item
            name="marks"
            label="Marks"
            rules={[{ required: true, message: 'Please enter marks' }]}
          >
            <InputNumber min={0} max={100} style={{ width: '100%' }} size="large" />
          </Form.Item>

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setMarksModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                Submit
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default TeacherDashboard;
